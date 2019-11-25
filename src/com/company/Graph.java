package com.company;


import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Graph {

    private HashMap<String, HashMap<String, Integer>> nodeConnections;

    public HashMap<String, HashMap<String, Integer>> getNodeConnections() {
        return nodeConnections;
    }

    public Graph() {
        this.nodeConnections = new HashMap<String, HashMap<String, Integer>>();
    }

    public HashMap<String, Integer> getNodeConnections(String nodeName){

        HashMap<String, Integer> connections = nodeConnections.computeIfAbsent(nodeName, k -> new HashMap<String, Integer>());

        return connections;
    }


    public Integer countTripsWrapper(String start, String end, Integer maxDepth){
        Integer res = 0;
        HashMap<String, Integer> connections = this.getNodeConnections(start);
        for (Map.Entry<String,Integer> adjacent:
                connections.entrySet()) {
            res += countTrips(adjacent.getKey(), end, 1, maxDepth);
        }
        return res;
    }

    private Integer countTrips(String start, String end, Integer currentDepth, Integer maxDepth) {
        if(currentDepth > maxDepth){
            return 0;
        }

        Map<String, Integer> connections = this.getNodeConnections(start);

        if(start.equals(end) && currentDepth > 1){

            return 1;
        }
        currentDepth++;
        for (Map.Entry<String,Integer> adjacent:
                connections.entrySet()) {
           return countTrips(adjacent.getKey(), end, currentDepth, maxDepth);
        }

        return 0;
    }

    public Integer countTripsByDistance(String start, String end, Integer currentDistance, Integer maxDistance){

        Integer res = 0;
        if(currentDistance >= maxDistance){
            return 0;
        }
        if(start.equals(end) && currentDistance > 0){
            res++;
        }
        Map<String, Integer> connections = this.getNodeConnections(start);

        for (Map.Entry<String,Integer> adjacent:
                connections.entrySet()) {
            res += countTripsByDistance(adjacent.getKey(), end, currentDistance + adjacent.getValue(), maxDistance);
        }

        return res;
    }

    //classic dijksta implementation
    public Integer shortestRouteDijkstra(String start, String end) {

        if (start.equals(end)) {//"shadowing" node because if destination equals origin dijkstra would output 0 in resulting array. shadowing would help to hack it
            end = start + start;
            HashMap<String, Integer> startNodeConnections = this.nodeConnections.get(start);
            this.getNodeConnections(end).putAll(startNodeConnections);

            for (HashMap.Entry<String, HashMap<String, Integer>> entry :
                    this.nodeConnections.entrySet()) {

                if (entry.getValue().containsKey(start)) {

                    entry.getValue().put(end, entry.getValue().get(start));
                }
            }
        }

        Map<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        HashSet<String> q = new HashSet<>();


        for (String s :
                this.nodeConnections.keySet()
        ) {
            if (!s.equals(start)) {
                distances.put(s, 9999999);//max distance, can't set it to infinity as it is described in algo, has to be really large
            }
            q.add(s);
        }


        while (!q.isEmpty()) {
            String minDistanceNode = distances
                    .entrySet()
                    .stream()
                    .filter(x -> q.contains(x.getKey()))
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getKey).get();


            q.remove(minDistanceNode);
            for (Map.Entry<String, Integer> entry :
                    this.nodeConnections.get(minDistanceNode).entrySet()) {

                Integer alt = distances.get(minDistanceNode) + entry.getValue();
                if (alt < distances.get(entry.getKey())) {
                    distances.replace(entry.getKey(), alt);
                }
            }

        }
        if (end.equals(start+start)) {//clean up
            getNodeConnections().remove(end);
        }
        return distances.get(end);

    }


    public String getDistance(String route) {
        Integer res = 0;
        Integer i = 0;
        String[] nodes = route.split("-");
        while (true) {

            Map<String, Integer> currentNodeConnections = getNodeConnections(nodes[i]);

            i++;
            if(i >= nodes.length)
                break;

            String shouldBeconnectedTo = nodes[i];

            Integer distanceTo = currentNodeConnections.get(shouldBeconnectedTo);
            if (distanceTo == null) {
                return "NO SUCH ROUTE";
            } else {
                res += distanceTo;
            }

        }
        return res.toString();
    }

}
