import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;

public abstract class AbstractAOC {

	protected Scanner scan;
	protected BufferedReader read;
	private String filename;

	public AbstractAOC(String filename) throws FileNotFoundException {
		this.filename = filename;
		setup();
	}

	private void setup() throws FileNotFoundException {
		scan = new Scanner(new File(filename));
		read = new BufferedReader(new FileReader(filename));
	}

	public abstract String part1();

	public abstract String part2();

	// helper method to use reflection to get class
	protected static Constructor<? extends AbstractAOC> getAOC(Class<? extends AbstractAOC> runClass) {
		try {
			// Get the constructor with the desired parameters
			Constructor<? extends AbstractAOC> constructor = runClass.getConstructor(String.class);
			return constructor;

		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e.toString());
		}
	}

	// helper method to run an object of the specified class
	// asks for filename and runs the object
	// loops forever if file not found
	protected static void run(Class<? extends AbstractAOC> runClass) {
		Scanner kb = new Scanner(System.in);
		Constructor<? extends AbstractAOC> constructor = getAOC(runClass);
		AbstractAOC day = null;
		while (day == null) {
			System.out.print("Enter input filename: ");
			String filename = kb.nextLine();
			try {
				// Create an instance of the class using the constructor
				day = constructor.newInstance(filename);
			} catch (InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
				System.out.println(filename + " not found.");
				e.printStackTrace();
			}
		}
		run(day);
		kb.close();
	}

	private static void run(AbstractAOC day) {
		System.out.print("The answer to part 1 is: ");
		System.out.println(day.part1());
		try {
			day.setup();
			System.out.print("The answer to part 2 is: ");
			System.out.println(day.part2());
		} catch (FileNotFoundException e) {
			System.out.println("error file was deleted during part1!");
			System.out.println("Cannot do part2 because file is missing.");
			e.printStackTrace();
		}

	}

	// helper method to run an object of the specified class
	// and filename, throws runtime exception when file not found
	protected static void run(Class<? extends AbstractAOC> runClass, String filename) {
		Constructor<? extends AbstractAOC> constructor = getAOC(runClass);
		AbstractAOC day = null;
		try {
			// Create an instance of the class using the constructor
			day = constructor.newInstance(filename);

		} catch (InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
			System.out.println(filename + " not found.");
			throw new RuntimeException(e.toString());
		}
		run(day);
	}
}
