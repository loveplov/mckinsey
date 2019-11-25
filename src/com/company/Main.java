package com.company;

import java.util.Map;

public class Main {

    public static void main(String[] args) {


        //do not really want to go through all the cases and implement interactive console app with function choices, could do on interview, but it is boring

        String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";

        Graph g = new Graph();
        for (String s: input.split(",")
             ) {

            s = s.trim();
            String nodeName = Character.toString(s.charAt(0));
            String connectedTo = Character.toString(s.charAt(1));

            Map<String, Integer> nodeConnections = g.getNodeConnections(nodeName);

            nodeConnections.put(connectedTo, Integer.parseInt(Character.toString(s.charAt(2))));
        }


        System.out.println(g.getDistance("A-B-C"));
        System.out.println(g.getDistance("A-D"));
        System.out.println(g.getDistance("A-D-C"));
        System.out.println(g.getDistance("A-E-B-C-D"));
        System.out.println(g.getDistance("A-E-D"));

        System.out.println(g.countTripsWrapper("C","C", 3));
        System.out.println(g.countTripsWrapper("A","C", 4));

        System.out.println(g.shortestRouteDijkstra("A","C"));
        System.out.println(g.shortestRouteDijkstra("B","B"));

        System.out.println(g.countTripsByDistance("C","C", 0,30));

    }


}

