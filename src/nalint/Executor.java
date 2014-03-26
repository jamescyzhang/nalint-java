package nalint;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Executor
{

	private static Runtime runtime = Runtime.getRuntime();
	private static BufferedReader reader;

	public static void exec(String command, String dir)
	{
		String[] str = { "/bin/sh", "-c", command };
		Process p;
		StringBuffer buffer = new StringBuffer();
		try
		{
			p = runtime.exec(dir, null, new File(dir));
			p.waitFor();
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				buffer.append(line + "\n");
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(buffer.toString());

	}

}
