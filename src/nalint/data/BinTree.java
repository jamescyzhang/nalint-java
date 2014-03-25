package nalint.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BinTree<T>
{

	private T root;
	private BinTree<T> lSon = null, rSon = null, father = null;
	private HashMap<T, BinTree<T>> loc = new HashMap<T, BinTree<T>>();

	public BinTree(T root)
	{
		this.root = root;
		loc.put(root, this);
	}

	public void addSon(T root, T son, char dir)
	{
		if (loc.containsKey(root))
		{
			loc.get(root).addSon(son, dir);
		}
		else
		{
			addSon(root, dir).addSon(son, dir);
		}
	}

	public BinTree<T> addSon(T son, char dir)
	{
		BinTree<T> t = new BinTree<T>(son);
		switch (dir)
		{
		case 'l':
			lSon = t;
			t.father = this;
			t.loc = this.loc;
			loc.put(son, t);
			return t;

		case 'r':
			rSon = t;
			t.father = this;
			t.loc = this.loc;
			loc.put(son, t);
			return t;
		default:
			break;
		}
		return t;

	}

	public BinTree<T> addSon(BinTree<T> t, char dir)
	{
		switch (dir)
		{
		case 'l':
			lSon = t;
			t.father = this;
			t.loc = this.loc;
			loc.put(t.root, t);
			return t;

		case 'r':
			rSon = t;
			t.father = this;
			t.loc = this.loc;
			loc.put(t.root, t);
			return t;
		default:
			break;
		}
		return t;
	}

	public BinTree<T> deleteSon(T son, char dir)
	{
		if (lSon.root.equals(son))
		{
			BinTree<T> t = loc.get(son);
			lSon = null;
			t.father = null;
			loc.remove(son);
			return t;
		}
		if (rSon.root.equals(son))
		{
			BinTree<T> t = loc.get(son);
			rSon = null;
			t.father = null;
			loc.remove(son);
			return t;
		}
		return null;
	}

	public BinTree<T> setAsFather(T fatherRoot, char dir)
	{
		BinTree<T> t = new BinTree<T>(root);
		t.addSon(this, dir);
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

	public BinTree<T> getFather()
	{
		return father;
	}

	public BinTree<T> getTree(T element)
	{
		return loc.get(element);
	}

	public Collection<T> getSuccessors(T root)
	{
		Collection<T> successors = new ArrayList<T>();
		BinTree<T> tree = getTree(root);
		if (tree != null)
		{
			BinTree<T> leaf = null;
			if ((leaf = tree.lSon) != null)
			{
				successors.add(leaf.root);
			}
			if ((leaf = tree.rSon) != null)
			{
				successors.add(leaf.root);
			}
		}
		return successors;
	}

	public void flrTraverse()
	{
		System.out.println(root);
		BinTree<T> leaf = null;
		if ((lSon != null || rSon != null))
		{
			System.out.println("{");
			if ((leaf = this.lSon) != null)
			{
				leaf.flrTraverse();
			}
			if ((leaf = this.rSon) != null)
			{
				leaf.flrTraverse();
			}
			System.out.println("}");
		}
	}

	public BinTree<T> getlSon()
	{
		return lSon;
	}

	public void setlSon(BinTree<T> lSon)
	{
		this.lSon = lSon;
	}

	public BinTree<T> getrSon()
	{
		return rSon;
	}

	public void setrSon(BinTree<T> rSon)
	{
		this.rSon = rSon;
	}

	public void setRoot(T root)
	{
		this.root = root;
	}

	public void setFather(BinTree<T> father)
	{
		this.father = father;
	}

}
