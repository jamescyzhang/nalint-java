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

	public static String exec(String command, String dir)
	{
		String dret = dir;
		if (command.startsWith("cd "))
		{
			String dest = command.substring(3);
			System.out.println("DEST: " + dest);
			if (dest.endsWith("../"))
			{
				dret = dret.substring(0, dret.lastIndexOf("/"));
			} else
				dret = dret + "/" + dest;
			return dret;
		}
		String[] str =
		{ "/bin/sh", "-c", command };
		Process p;
		StringBuffer buffer = new StringBuffer();
		ProcessBuilder pBuilder = null;
		try
		{
			p = runtime.exec(command, null, new File(dir));
			p.waitFor();
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				buffer.append(line + "nullnull\n");
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(buffer.toString());
		return dret;
	}

}
