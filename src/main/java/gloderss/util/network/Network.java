package gloderss.util.network;

import gloderss.util.random.RandomUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network<E> {
	
	private Map<E, List<E>>	network;
	
	private int							degree;
	
	
	/**
	 * Constructor
	 * 
	 * @param none
	 * @return none
	 */
	public Network() {
		this.network = new HashMap<E, List<E>>();
		this.degree = 0;
	}
	
	
	/**
	 * Build the Barabasi & Albert scale-free network with the list of nodes
	 * provided
	 * 
	 * @param nodes
	 *          List of nodes to structure as a scale-free network
	 * @return none
	 */
	public void generateBarabasiAlbertScaleFreeNetwork(Collection<E> nodes) {
		
		if((nodes != null) && (!nodes.isEmpty())) {
			this.network = new HashMap<E, List<E>>();
			this.degree = 0;
			for(E newNode : nodes) {
				
				List<E> newNeighbors = new ArrayList<E>();
				boolean hasConnection = false;
				while((!this.network.isEmpty()) && (!hasConnection)) {
					
					for(E oldNode : this.network.keySet()) {
						List<E> oldNeighbors = this.network.get(oldNode);
						
						if((this.degree == 0)
								|| ((RandomUtil.nextDouble() < ((double) oldNeighbors.size() / (double) this.degree)))) {
							newNeighbors.add(oldNode);
							
							oldNeighbors.add(newNode);
							this.network.put(oldNode, oldNeighbors);
							
							hasConnection = true;
							this.degree++;
						}
					}
				}
				
				this.network.put(newNode, newNeighbors);
			}
		}
	}
	
	
	/**
	 * 
	 */
	public void generateMeshNetwork(Collection<E> nodes) {
		
		if((nodes != null) && (!nodes.isEmpty())) {
			this.network = new HashMap<E, List<E>>();
			for(E newNode : nodes) {
				List<E> newNeighbors = new ArrayList<E>();
				newNeighbors.addAll(nodes);
				newNeighbors.remove(newNode);
				this.network.put(newNode, newNeighbors);
			}
			this.degree = (int) ((double) (nodes.size() * (nodes.size() - 1)) / (double) 2);
		}
	}
	
	
	/**
	 * Get the number of edges of the network
	 * 
	 * @param none
	 * @return Number of edges of the network
	 */
	public int getDegree() {
		return this.degree;
	}
	
	
	/**
	 * Get the number of neighbors of a node
	 * 
	 * @param node
	 *          Node of the network
	 * @return Number of neighbors
	 */
	public int getDegree(E node) {
		int nodeDegree = 0;
		
		if(this.network.containsKey(node)) {
			List<E> neighbors = this.network.get(node);
			if(neighbors != null) {
				nodeDegree = neighbors.size();
			}
		}
		
		return nodeDegree;
	}
	
	
	/**
	 * Get the neighbors of a node
	 * 
	 * @param node
	 *          Node to obtain the neighbors from
	 * @return List of neighbors
	 */
	public List<E> getNeighbors(E node) {
		List<E> neighbors;
		
		if(this.network.containsKey(node)) {
			neighbors = this.network.get(node);
		} else {
			neighbors = new ArrayList<E>();
		}
		
		return neighbors;
	}
}