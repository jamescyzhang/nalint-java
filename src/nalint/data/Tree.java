package nalint.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Tree<T>
{

	private T root;
	private ArrayList<Tree<T>> sons = new ArrayList<Tree<T>>();
	private Tree<T> father = null;
	private HashMap<T, Tree<T>> loc = new HashMap<T, Tree<T>>();

	public Tree(T root)
	{
		this.root = root;
		loc.put(root, this);
	}

	public void addSon(T root, T son)
	{
		if (loc.containsKey(root))
		{
			loc.get(root).addSon(son);
		} else
		{
			addSon(root).addSon(son);
		}
	}

	public Tree<T> addSon(T son)
	{
		Tree<T> t = new Tree<T>(son);
		sons.add(t);
		t.father = this;
		t.loc = this.loc;
		loc.put(son, t);
		return t;
	}
	public Tree<T> addSon(Tree<T> t)
	{
		sons.add(t);
		t.father = this;
		t.loc = this.loc;
		loc.put(t.root, t);
		return t;
	}

	public Tree<T> deleteSon(T son)
	{
		if (loc.containsKey(son))
		{
			Tree<T> t = loc.get(son);
			sons.remove(t);
			t.father = null;
			loc.remove(son);
			return t;
		}
		return null;
	}

	public void deleteSon(T root, T son)
	{
		if (loc.containsKey(root))
		{
			loc.get(root).deleteSon(son);
		}
	}

	public Tree<T> setAsFather(T fatherRoot)
	{
		Tree<T> t = new Tree<T>(root);
		t.sons.add(this);
		this.father = t;
		t.loc = this.loc;
		t.loc.put(root, this);
		t.loc.put(fatherRoot, t);
		return t;

	}

	public T getRoot()
	{
		return root;
	}

	public ArrayList<Tree<T>> getSons()
	{
		return sons;
	}

	public Tree<T> getFather()
	{
		return father;
	}

	public Tree<T> getTree(T element)
	{
		return loc.get(element);
	}

	public Collection<T> getSuccessors(T root)
	{
		Collection<T> successors = new ArrayList<T>();
		Tree<T> tree = getTree(root);
		if (tree != null)
		{
			for (Tree<T> leaf : tree.sons)
			{
				successors.add(leaf.root);
			}
		}
		return successors;
	}
	
	public void flrTraverse()
	{
		System.out.println(root);
		if (!sons.isEmpty())
		{
			System.out.println("{");
			for (Tree<T> s : sons)
			{
				s.flrTraverse();
			}
			System.out.println("}");
		}
	}

}
