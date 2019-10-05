package spatialindex.rtree;


public class SQpoint extends SPoint{
	private int id;
	public SQpoint(int id,double longitude,double latitude) {
		super(longitude, latitude);
		this.id=id;
	}
	public int getId() {
		return id;
	}
}
