package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

import map.Enums.TileType;

public class PathFinder {

	IndexedGraph graph;
	HashMap<Vector2, Node> allNodes;
	Chunk map;
	Heuristic<Node> heuristic;
	IndexedAStarPathFinder<Node> pathFinder;
	GrapPathImp result;
	GrapPathImp connections;
	public PathFinder(Chunk map) {
		this.map = map;
		init();
		
	}
	
	public ArrayList<Node> findPath(Vector2 start, Vector2 end) {
		return findPath(allNodes.get(start), allNodes.get(end));
	}
	
	public ArrayList<Node> findPath(Node startNode, Node endNode) {
		System.out.println("Finding path between" + startNode.pos + ", " + endNode.pos);
		pathFinder = new IndexedAStarPathFinder<Node>(graph);
	
		System.out.println(pathFinder.searchNodePath(startNode, endNode, heuristic, result ));
		
		//PathFinderRequest request = new PathFinderRequest(startNode, endNode, heuristic, resultPath);
		//request.statusChanged = true;
		//System.out.println(request.pathFound + " :  path Found");
		//boolean success = pathFinder.search(request, 1000*1000*1000);
		//System.out.println("success: " + success);
		ArrayList result2 = new ArrayList();
		
		Iterator it = result.iterator();
		while (it.hasNext()) {
			Node node = (Node) it.next();
			System.out.println(it.hasNext() + " " + node.pos + " ");
			result2.add(node);
		} 
		
		System.out.println(result2.size() + " lopputulos");
		
		return result2;
			
	}

	private void init() {
		this.map = map;
		initAllNodes();
		initGraph();
		initHeuristic();
		pathFinder = new IndexedAStarPathFinder(graph);
	}

	
	private void initAllNodes() {
		allNodes = new HashMap<Vector2, Node>();

		int width = map.SIZE;
		int height = map.SIZE;
		int index = 0;
		Array <Node> tmp = new Array<Node>();
		// Create nodes
		for (int i = 0; i < Chunk.SIZE; i++) {
			for (int j = 0; j < Chunk.SIZE; j++) {
				Node node = new Node((float)i, (float)j, index++);
				//System.out.println(node.index + " <- index x --> " + node.getX());
				//System.out.println("Create nodes " + node.pos + ", i: " +i + " j: " +j);
				allNodes.put(new Vector2((float)i, (float)j), node);
				tmp.add(node);
			}
			
		result = new GrapPathImp(tmp);
		}

		// Node connections

		for (int i = 1; i < Chunk.SIZE - 2; i++) {
			for (int j = 1; j < Chunk.SIZE- 2; j++) {
				Tile target = map.getTile(i, j);
				Tile up = map.getTile(i, j + 1);
				Tile left = map.getTile(i - 1, j);
				Tile right = map.getTile(i + 1, j);
				Tile down = map.getTile(i, j - 1);
				
				Node source = allNodes.get(new Vector2(i, j));
				Array<CustomConnection> connections = new Array<CustomConnection>();
				if (target.getTileType() == TileType.GRASS) {
					

				addConnection(connections, new Vector2(i,j), new Vector2(i - 1, j));

				addConnection(connections, new Vector2(i,j), new Vector2(i, j + 1));
				addConnection(connections, new Vector2(i,j), new Vector2(i, j - 1));


				addConnection(connections, new Vector2(i,j), new Vector2(i + 1 , j));
				System.out.println("Source: " + source.pos + "neighbours: " +connections + " " + target.tiletype);
				}
				

				

				/**if (target.getTileType() == TileType.GRASS || target.getTileType() == TileType.SAND || target.tiletype == TileType.WATER) {
					Node source = (Node) allNodes.get(new Vector2(i, j));
					Array<CustomConnection> connections = new Array();
					if (j > 0 && down.getTileType() == TileType.GRASS) {
						addConnection(connections, new Vector2(i,j), new Vector2(i, j + 1));
						System.out.println("source: "+source.pos + " connectionsDOWN: "+connections);
					}
				}

				if (target.getTileType() == TileType.GRASS || target.getTileType() == TileType.SAND || target.tiletype == TileType.WATER) {
					Node source = (Node) allNodes.get(new Vector2(i, j));
					Array<CustomConnection> connections = new Array();
					if (i > 0 && left.getTileType() == TileType.GRASS) {
						addConnection(connections, new Vector2(i,j), new Vector2(i - 1, j));
					}
				}

				if (target.getTileType() == TileType.GRASS || target.getTileType() == TileType.SAND || target.tiletype == TileType.WATER) {
					Node source = (Node) allNodes.get(new Vector2(i, j));
					Array<CustomConnection> connections = new Array();
					if (i != 39 && right.getTileType() == TileType.GRASS) {
						addConnection(connections, new Vector2(i,j), new Vector2(i + 1, j));
					}
				}

				if (target.getTileType() == TileType.GRASS || target.getTileType() == TileType.SAND || target.tiletype == TileType.WATER) {
					Node source = (Node) allNodes.get(new Vector2(i, j));
					Array<CustomConnection> connections = new Array();
					if (j != 39 && up.getTileType() == TileType.GRASS) {
						addConnection(connections, new Vector2(i,j), new Vector2(i, j - 1));
					}
				} **/
			}
		}
		
	}
	
	private void initHeuristic() {
		heuristic = new Heuristic<Node>() {
			public float estimate(Node startNode, Node endNode) {
				System.out.println("Estimate: " + Math.max(Math.abs(startNode.pos.x - endNode.pos.x), Math.abs(startNode.pos.y - endNode.pos.y)));
				return Math.max(Math.abs(startNode.pos.x - endNode.pos.x), Math.abs(startNode.pos.y - endNode.pos.y));
			}
		};
	}
	
	private void addConnection(Array<CustomConnection> connections, Vector2 from, Vector2 to) {
		float cost = 2;
		if ((from.x == to.x && from.y != to.y) || (from.x != to.x && from.y == to.y)) {
			cost = 1;
		}
		
		Node fromNode = (Node) allNodes.get(from);
		Node toNode = (Node) allNodes.get(to);
		

			connections.add(new CustomConnection(fromNode, toNode, cost));
			
	}
	
	private void initGraph() {
		graph = new IndexedGraph<Node>() {

			@Override
			public Array getConnections(Node n) {
				if (n.getClass().equals(Node.class)) {
					
					System.out.println("Pääseekö palauttaa yhteydet" + n.getConnections());
					return ((Node) n).getConnections();
				}

				return null;
			}


			@Override
			public int getNodeCount() {
				return 50 * 50;
			}


			@Override
			public int getIndex(Node node) {
				//if(node.getClass().isAssignableFrom(Node.class)) {
					return ((Node) node).getIndex();
				//}
			}

		};
	}
}
