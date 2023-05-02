package animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
public class Shake {
    private TranslateTransition translateTransition;

    public Shake(Node node) {
        translateTransition = new TranslateTransition(Duration.millis(75), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(11f);
        translateTransition.setCycleCount(4);
        translateTransition.setAutoReverse(true);
    }

    public void playAnimation() {
        translateTransition.playFromStart();
    }
}
