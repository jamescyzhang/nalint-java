package nalint.manager.usr;

public class User
{
	private String username, fullName;
	private boolean isAdmin;

	public User()
	{
		this("", "", false);
	}

	public User(String username, String fullName, boolean isAdmin)
	{
		super();
		this.username = username;
		this.fullName = fullName;
		this.isAdmin = isAdmin;
	}

	public static User getCurrentUser()
	{
		// code
		return null;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}

}
