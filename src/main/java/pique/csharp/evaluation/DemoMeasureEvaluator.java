package pique.csharp.evaluation;

import pique.evaluation.Evaluator;
import pique.model.ModelNode;

public class DemoMeasureEvaluator extends Evaluator {

    /**
     * Don't utilize thresholds from benchmark repository (due to n = ~1 for demo).
     * Instead, just return the weighted sums of normalized values.
     */

    @Override
    public double evaluate(ModelNode modelNode) {

        double value = modelNode.getChildren().values().stream()
                .mapToDouble(ModelNode::getValue)
                .sum();
        return modelNode.getNormalizerObject().normalize(value);

    }

}
