package gloderss.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NormSalienceAdapter extends
		XmlAdapter<NormsSalienceConf, Map<Integer, Double>> {
	
	@Override
	public Map<Integer, Double> unmarshal(NormsSalienceConf value)
			throws Exception {
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		for(NormSalienceConf norm : value.getNormsSalience()) {
			map.put(norm.getId(), norm.getSalience());
		}
		return map;
	}
	
	
	@Override
	public NormsSalienceConf marshal(Map<Integer, Double> value) throws Exception {
		NormsSalienceConf normsSalienceConf = new NormsSalienceConf();
		List<NormSalienceConf> normSalienceList = new ArrayList<NormSalienceConf>();
		
		for(Entry<Integer, Double> entry : value.entrySet()) {
			NormSalienceConf normSalienceConf = new NormSalienceConf();
			normSalienceConf.setId(entry.getKey());
			normSalienceConf.setSalience(entry.getValue());
			normSalienceList.add(normSalienceConf);
		}
		
		normsSalienceConf.setNormsSalience(normSalienceList);
		
		return normsSalienceConf;
	}
}