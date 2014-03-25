package nalint.data;

public class Word
{
	String val;

	public enum WordType
	{
		NOUN, VERB, ARTICLE, ADJECTIVE, ADVERB, CONJUNCTION, INTERJECTION, PREPOSITION, PRONOUN, PARTICLE, SENTENCE, PHRASE, MISC
	}

	WordType partOfSpeech;
	
	String cmd;

	public Word()
	{
		this("", WordType.MISC, "");
	}

	public Word(String str, WordType type, String cmd)
	{
		val = str;
		partOfSpeech = type;
		this.cmd = cmd;
	}

	public String getVal()
	{
		return val;
	}

	public WordType getPartOfSpeech()
	{
		return partOfSpeech;
	}
	
	@Override
	public String toString()
	{
		return ("[" + val + " / " + partOfSpeech + " | " + cmd + "]");
	}

	public String getCmd()
	{
		return cmd;
	}

}
