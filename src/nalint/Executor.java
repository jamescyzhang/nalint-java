package nalint;

import java.io.IOException;
import java.util.Scanner;

public class Executor
{

	private static Runtime runtime = Runtime.getRuntime();
	private static Scanner sc;

	public static void exec(String command)
	{
		Process p;
		try
		{
			p = runtime.exec(command);
			p.waitFor();
			sc = new Scanner(p.getInputStream());
			String line = "";
			while ((line = sc.nextLine()) != null)
			{
				System.out.println(line + "\n");
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
