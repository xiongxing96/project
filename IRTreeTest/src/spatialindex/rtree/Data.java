package spatialindex.rtree;

import spatialindex.spatialindex.IData;
import spatialindex.spatialindex.IShape;
import spatialindex.spatialindex.Region;
import java.io.Serializable;
public class Data implements IData, Serializable
	{
		int m_id;
		Region m_shape;
		byte[] m_pData;
		
		double weight;

		Data(byte[] pData, Region mbr, int id) { m_id = id; m_shape = mbr; m_pData = pData; }
		
		public Data(double w, Region mbr, int id) { m_id = id; m_shape = mbr; weight = w; }

		public int getIdentifier() { return m_id; }
		public IShape getShape() { return m_shape; }
		public byte[] getData()
		{
			byte[] data = new byte[m_pData.length];
			System.arraycopy(m_pData, 0, data, 0, m_pData.length);
			return data;
		}
		
		public double getWeight(){
			return weight;
		}
	
}
