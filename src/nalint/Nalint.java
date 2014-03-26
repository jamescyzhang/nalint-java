package nalint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import nalint.data.BinTree;
import nalint.data.Word;
import nalint.data.Word.WordType;

public class Nalint
{
	// Properties
	String osName;

	// Togglable modes
	boolean exec;

	// I/O
	Scanner stdin;

	// Variables
	String cmd;
	Vector<String> vw;
	BinTree<Word> tree;
	HashMap<String, Word> dict;

	/**
	 * @author James Precondition: The program is not initialized.
	 */
	private void init()
	{
		// Initialize i/o
		stdin = new Scanner(System.in);
		// Detect OS
		osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("nux") >= 0) // Some cort of Linux
		{
			exec = true;
		}
		else
		// Anything else
		{
			System.err.println("OS \"" + osName
					+ "\"detected, but not Linux. Running without execution.");
			exec = false;
		}
		// Load dictionary
		dict = new HashMap<>();
		try
		{
			Scanner sc = new Scanner(new File("files/dict.txt"));
			sc.nextLine();
			while (sc.hasNext())
			{
				String buffer = sc.nextLine();
				/*
				 * Index Meaning 0 word 1 part of speech 2 inflection (plural,
				 * 3p, etc)
				 */
				String[] word = buffer.split("\\t");
				System.out.println("Nalint.init(): " + word[0] + " " + word[1]
						+ " " + word[2]);
				WordType type = null;
				if (word[1].equals("noun"))
				{
					type = WordType.NOUN;
				}
				else if (word[1].equals("art"))
				{
					type = WordType.ARTICLE;
				}
				else if (word[1].equals("verb"))
				{
					type = WordType.VERB;
				}
				else if (word[1].equals("adj"))
				{
					type = WordType.ADJECTIVE;
				}
				else if (word[1].equals("adv"))
				{
					type = WordType.ADVERB;
				}
				else if (word[1].equals("conj"))
				{
					type = WordType.CONJUNCTION;
				}
				else if (word[1].equals("prep"))
				{
					type = WordType.PREPOSITION;
				}
				else if (word[1].equals("pron"))
				{
					type = WordType.PRONOUN;
				}
				else if (word[1].equals("phrase"))
				{
					type = WordType.PHRASE;
				}
				else if (word[1].equals("part"))
				{
					type = WordType.PARTICLE;
				}
				else if (word[1].equals("interj"))
				{
					type = WordType.INTERJECTION;
				}
				else
				{
					type = WordType.MISC;
				}
				if (word[2].equals("n/a"))
				{
					word[2] = "";
				}
				dict.put(word[0], new Word(word[0], type, word[2]));
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			System.err
					.println("The dictionary file cannot be found or is corrupt. Please replace the file by obtaining another copy of this software.");
			System.err.println("Exiting in 5 seconds...");
			try
			{
				int sec = 5;
				while (sec > 0)
				{
					Thread.sleep(1000);
					System.err.println(--sec);
				}
			}
			catch (InterruptedException e1)
			{
				Thread.currentThread().interrupt();
			}
			System.exit(1);
			e.printStackTrace();
		}
		// initialize variables
		cmd = "";
		vw = new Vector<String>();
		tree = new BinTree<Word>(new Word("", WordType.SENTENCE, ""));
	}

	/**
	 * 
	 * @param strings
	 * @param type
	 * @return the first word that has the part of speech denoted as
	 *         <code>type</code> in <code>strings</code>.
	 */
	@SuppressWarnings("unused")
	private Word findPOS(Vector<String> strings, WordType type)
	{
		for (String str : vw)
		{
			if (dict.containsKey(str))
			{
				if (dict.get(str).getPartOfSpeech() == type)
				{
					return dict.get(str);
				}
			}
		}
		return null;
	}

	private int getIdxPOS(Vector<String> strings, WordType type)
	{
		for (int i = 0; i < strings.toArray().length; i++)
		{
			if (dict.containsKey(strings.get(i)))
			{
				if (dict.get(strings.get(i)).getPartOfSpeech() == type)
				{
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Run
	 */
	private void run()
	{
		while (true)
		{
			// Prompt
			System.out.print("> ");

			// Handle input
			cmd = stdin.nextLine();
			if (cmd.equals(""))
			{
				continue;
			}
			if (cmd.equals("#curdir$"))
			{
				System.out.println("DEBUG: cd");
			}
			if (exec)
				Executor.exec("pwd");
			vw = new Vector<String>(Arrays.asList(cmd.split("\\s+")));
			/*
			 * for (int i = 0; i < vw.size(); i++) { // You may want to check
			 * for a non-word character before blindly if
			 * (vw.get(i).charAt(index)) //vw.set(i,
			 * vw.get(i).replaceAll("[^\\w]", "")); }
			 */

			for (String str : vw)
			{
				if (!Character.isLetterOrDigit(str.charAt(str.length() - 1)))
				{
					str.replace(str.charAt(str.length() - 1), '\0');
				}
			}
			for (String str : vw)
			{

				System.err.println();
				System.out.print(str + " : ");
				String ss = str;

				boolean fl = false;
				do
				{
					if (dict.containsKey(ss))
					{
						System.out.println("get");
						fl = true;
						System.out.println(dict.get(ss).getPartOfSpeech());
						break;
					}
					else
					{
						System.out.println("noget");
						ss = ss.substring(0, ss.length() - 1);
					}
				}
				while (ss.length() > 0);
				if (fl)
				{
					if (!dict.containsKey(str))
					{
						dict.put(str, new Word(str, dict.get(ss)
								.getPartOfSpeech(), dict.get(ss).getCmd()));
					}
				}
				if (!fl)
				{
					System.out.println("MISC");
					dict.put(str, new Word(str, WordType.MISC, ""));
				}
			}

			tree = createBinTree(tree, vw);
			tree.flrTraverse();
			// CHECKPOINT
			String command = generate(tree);
			System.out.println(command);
			if (exec)
				Executor.exec(command);
		}
	}

	private String generate(BinTree<Word> tree)
	{
		String ret = "";
		if (tree.getlSon() != null)
		{
			ret += generate(tree.getlSon());
		}

		if (tree.getRoot().getCmd() == "")
		{
			if (tree.getRoot().getPartOfSpeech() == WordType.MISC)
			{
				ret += tree.getRoot().getVal();
			}
		}
		else
		{
			ret += tree.getRoot().getCmd() + " ";
		}

		if (tree.getrSon() != null)
		{
			ret += generate(tree.getrSon());
		}

		return ret;
	}

	/**
	 * @param tree
	 */
	private BinTree<Word> createBinTree(BinTree<Word> tr, Vector<String> vw)
	{
		if (vw.size() == 1)
		{
			System.out.println("LEAF: " + vw.get(0));
			return new BinTree<Word>(dict.get(vw.get(0)));
		}
		if (vw.size() == 0)
		{
			return new BinTree<Word>(null);
		}
		/*
		 * Look for split point Priority: conj > verb > noun > adjective >
		 * adverb > interj
		 */
		BinTree<Word> t = tr;
		Word split = null;
		int idx = 0;
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.CONJUNCTION);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.VERB);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.PREPOSITION);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.NOUN);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.ADJECTIVE);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.ARTICLE);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.PARTICLE);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.ADVERB);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.INTERJECTION);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{
			idx = getIdxPOS(vw, WordType.MISC);
			if (idx != -1)
				split = dict.get(vw.get(idx));
		}
		if (split == null)
		{

		}
		System.out.println(idx + split.getVal());
		BinTree<Word> ret = new BinTree<Word>(split);
		if (idx > 0)
		{
			System.out.println("L son");
			ret.addSon(createBinTree(t, new Vector<>(vw.subList(0, idx))), 'l');
		}
		if (idx < vw.size())
		{
			System.out.println("R son");
			ret.addSon(
					createBinTree(t,
							new Vector<>(vw.subList(idx + 1, vw.size()))), 'r');
		}
		return ret;
	}

	public static void main(String[] args)
	{
		Nalint prog = new Nalint();
		prog.init();
		prog.run();
	}

}