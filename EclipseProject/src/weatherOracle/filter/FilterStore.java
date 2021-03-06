package weatherOracle.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import weatherOracle.control.MainControl;
import weatherOracle.activity.HomeMenuActivity;

import android.content.Context;
import android.util.Pair;

/**
 * keeps a single up to data copy of the filters on disk, and can update and
 * provide it on demand. Any version requested gets a version number that is
 * larger than all previous differing version returned since launch (The version
 * number may increase without changes, and may reset on launch). This is all
 * fully thread safe! All combinations of calls from differing threads are
 * allowed.
 * 
 */
public abstract class FilterStore {
	
	/**
	 * The name of the file on the Android disk to store data
	 */
	private static String fileName = "filterFile";
	
	/**
	 * The main Application Context
	 */
	public static Context ctx = HomeMenuActivity.mainContext;
	
	/**
	 * The current Filter version number
	 */
	private static int versionNumber = 0;
	
	/**
	 * The byte representation of the Filters
	 */
	private static byte[] data;
	
	/**
	 * Replace the currently saved version, and increment the filter version
	 * number.
	 * 
	 * @param filters
	 *            A list of filter to be saved.
	 * @throws IllegalStateException if the main application Context is not set
	 */
	public synchronized static void setFilters(List<Filter> filters) {
		if (ctx == null) {
			throw new IllegalStateException("Context has not been set!");
		}
		
		ByteArrayOutputStream fbos = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(fbos);
			out.writeObject(filters);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		data = fbos.toByteArray();
		modified();
		versionNumber++;
		writeOut();
	}

	// save data to file
	private synchronized static void writeOut() {
		try {
			FileOutputStream fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(data);
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}

	// read data from file, if there is a file
	private synchronized static void readIn() {
		try {
			FileInputStream fis = ctx.openFileInput(fileName);
			List<Byte> byteList = new ArrayList<Byte>();
			int b;
			while ((b = fis.read()) != -1) {
				byteList.add(new Byte((byte)b));
			}
			byte[] array = new byte[byteList.size()];
			
			for (int i = 0; i < array.length; i++) {
				array[i] = byteList.get(i).byteValue();
			}
			data = array;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}

	/**
	 * Retrieves the filters from persistent memory
	 * 
	 * @return A Pair containing a list of Filters and a version number
	 * @throws IllegalStateException if the main application Context is not set
	 */
	public synchronized static Pair<List<Filter>, Integer> getFilters() {
		if (ctx == null) {
			throw new IllegalStateException("Context has not been set!");
		}
		
		if (data == null) { // no data set or read in yet
			readIn();
			if (data == null) { // nothing to read/read failed
				return new Pair<List<Filter>, Integer>(new ArrayList<Filter>(),
						versionNumber);
			}
		}

		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(data));
			List<Filter> copy = (List<Filter>) in.readObject();
			return new Pair<List<Filter>, Integer>(copy, versionNumber);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert (false);
		return null;
	}

	private static void modified() {
		MainControl.startUpdate();
	}
}
