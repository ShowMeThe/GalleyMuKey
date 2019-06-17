package showmethe.github.kframework.parallaxbacklayout


import showmethe.github.kframework.parallaxbacklayout.widget.ParallaxBackLayout
import java.lang.annotation.RetentionPolicy
import kotlin.annotation.Retention


@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ParallaxBack(
    /**
     * Edge edge.
     *
     * @return the edge
     */
    val edge: Edge = Edge.LEFT,
    /**
     * The  slide Transform.
     *
     * @return the layout type ,default parallax
     */
    val layout: Layout = Layout.PARALLAX,
    /**
     * The slide distance
     *
     * @return default edge
     */
    val edgeMode: EdgeMode = EdgeMode.EDGE
) {

    /**
     * slide edge
     */
    enum class Edge constructor(
        @get:ParallaxBackLayout.Edge
        val value: Int
    ) {

        LEFT(ViewDragHelper.EDGE_LEFT), RIGHT(ViewDragHelper.EDGE_RIGHT), TOP(ViewDragHelper.EDGE_TOP), BOTTOM(
            ViewDragHelper.EDGE_BOTTOM
        )
    }

    /**
     * The enum Layout.
     */
    enum class Layout constructor(
        @get:ParallaxBackLayout.LayoutType
        val value: Int
    ) {
        PARALLAX(ParallaxBackLayout.LAYOUT_PARALLAX), COVER(ParallaxBackLayout.LAYOUT_COVER), SLIDE(ParallaxBackLayout.LAYOUT_SLIDE)

    }

    /**
     * Slide mode.
     */
    enum class EdgeMode  constructor(
        @get:ParallaxBackLayout.EdgeMode
        val value: Int
    ) {
        FULLSCREEN(ParallaxBackLayout.EDGE_MODE_FULL),
        EDGE(ParallaxBackLayout.EDGE_MODE_DEFAULT)

    }

}
