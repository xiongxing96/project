package spatialindex.rtree;

import java.util.ArrayList;

public class NNE {
	private ArrayList<NNEntry>entry;
	private int id;
	

	public NNE(ArrayList<NNEntry> entry, int id) {
		super();
		this.entry = entry;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<NNEntry> getEntry() {
		return entry;
	}
	public void setEntry(ArrayList<NNEntry> entry) {
		this.entry = entry;
	}
	
}
