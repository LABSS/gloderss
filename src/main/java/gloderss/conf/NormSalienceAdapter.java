package gloderss.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NormSalienceAdapter
    extends XmlAdapter<NormsSalienceConf, Map<Integer, NormSalienceConf>> {
  
  @Override
  public Map<Integer, NormSalienceConf> unmarshal(NormsSalienceConf value)
      throws Exception {
    Map<Integer, NormSalienceConf> map = new HashMap<Integer, NormSalienceConf>();
    for(NormSalienceConf norm : value.getNormsSalience()) {
      map.put(norm.getId(), norm);
    }
    return map;
  }
  
  
  @Override
  public NormsSalienceConf marshal(Map<Integer, NormSalienceConf> value)
      throws Exception {
    NormsSalienceConf normsSalienceConf = new NormsSalienceConf();
    List<NormSalienceConf> normSalienceList = new ArrayList<NormSalienceConf>();
    
    for(Entry<Integer, NormSalienceConf> entry : value.entrySet()) {
      NormSalienceConf normSalienceConf = new NormSalienceConf();
      normSalienceConf.setId(entry.getKey());
      normSalienceConf.setCompliance(entry.getValue().getCompliance());
      normSalienceConf.setViolation(entry.getValue().getViolation());
      normSalienceConf.setObsCompliance(entry.getValue().getObsCompliance());
      normSalienceConf.setObsViolation(entry.getValue().getObsViolation());
      normSalienceConf.setPunishment(entry.getValue().getPunishment());
      normSalienceConf.setSanction(entry.getValue().getSanction());
      normSalienceConf
          .setInvocationCompliance(entry.getValue().getInvocationCompliance());
      normSalienceConf
          .setInvocationViolation(entry.getValue().getInvocationViolation());
      normSalienceList.add(normSalienceConf);
    }
    
    normsSalienceConf.setNormsSalience(normSalienceList);
    
    return normsSalienceConf;
  }
}