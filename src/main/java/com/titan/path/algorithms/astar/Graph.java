package com.titan.path.algorithms.astar;

import com.titan.path.core.IGraphNode;
import com.titan.path.web.exceptions.IllegalArgumentNameException;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends IGraphNode> {

    private final Set<T> nodes;

    private final Map<String, Set<String>> connections;

    public Graph(Set<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public T getNode(String id) {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentNameException("No node found with ID " + id));
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }

}
