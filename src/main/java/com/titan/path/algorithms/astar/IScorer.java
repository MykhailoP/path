package com.titan.path.algorithms.astar;

import com.titan.path.core.IGraphNode;

public interface IScorer<T extends IGraphNode> {

    double computeCost(T from, T to);

}
