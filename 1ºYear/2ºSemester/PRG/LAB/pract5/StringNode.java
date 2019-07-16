package pract5;

public class StringNode{
	protected String data;
	protected StringNode next;

	public StringNode(String v){
		this(v, null);
	}
	public StringNode(String v, StringNode n){
		data = v;
		next = n;
	}

	public String getData(){return data;}
	public StringNode getNext(){return next;}

	public void setValue(String v){data = v;}
	public void setNext(StringNode n){next = n;}
}
