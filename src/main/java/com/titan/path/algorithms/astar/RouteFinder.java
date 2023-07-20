package com.titan.path.algorithms.astar;

import com.titan.path.core.IGraphNode;
import com.titan.path.web.exceptions.FailedOperationException;

import java.util.*;
import java.util.stream.Collectors;

public class RouteFinder<T extends IGraphNode> {

    private final Graph<T> graph;
    private final IScorer<T> nextNodeScorer;
    private final IScorer<T> targetScorer;

    public RouteFinder(Graph<T> graph, IScorer<T> nextNodeScorer, IScorer<T> targetScorer) {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    public List<T> findRoute(T from, T to) {
        Map<T, RouteNode<T>> allNodes = new HashMap<>();
        Queue<RouteNode> openSet = new PriorityQueue<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
        allNodes.put(from, start);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            RouteNode<T> next = openSet.poll();
            if (next.getCurrent().equals(to)) {

                List<T> route = new ArrayList<>();
                RouteNode<T> current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);

               /* for(T t: route){
                    double score = targetScorer.computeCost(from, t);
                    t.setScore(score);
                }

                route.sort(Comparator.comparing(IGraphNode::getScore)); */

                return route;
            }

            graph.getConnections(next.getCurrent()).forEach(connection -> {
                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                if (nextNode.getRouteScore() > newScore) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.getCurrent().setScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                }
            });
        }

        throw new FailedOperationException("No route found");
    }


}
