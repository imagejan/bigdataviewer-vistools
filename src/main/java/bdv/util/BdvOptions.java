/*
 * #%L
 * BigDataViewer core classes with minimal dependencies
 * %%
 * Copyright (C) 2012 - 2015 BigDataViewer authors
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package bdv.util;

import org.scijava.ui.behaviour.io.InputTriggerConfig;

import bdv.BehaviourTransformEventHandler3D;
import bdv.viewer.ViewerOptions;
import bdv.viewer.ViewerPanel;
import bdv.viewer.render.AccumulateProjector;
import bdv.viewer.render.AccumulateProjectorARGB;
import bdv.viewer.render.AccumulateProjectorFactory;
import bdv.viewer.render.MultiResolutionRenderer;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.ui.TransformEventHandlerFactory;

/**
 * Optional parameters for {@link ViewerPanel}.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class BdvOptions
{
	public final Values values = new Values();

	/**
	 * Create default {@link BdvOptions}.
	 * @return default {@link BdvOptions}.
	 */
	public static BdvOptions options()
	{
		return new BdvOptions();
	}

	/**
	 * Set width of {@link ViewerPanel} canvas.
	 */
	public BdvOptions width( final int w )
	{
		values.width = w;
		return this;
	}

	/**
	 * Set height of {@link ViewerPanel} canvas.
	 */
	public BdvOptions height( final int h )
	{
		values.height = h;
		return this;
	}

	/**
	 * Set the number and scale factors for scaled screen images.
	 *
	 * @param s
	 *            Scale factors from the viewer canvas to screen images of
	 *            different resolutions. A scale factor of 1 means 1 pixel in
	 *            the screen image is displayed as 1 pixel on the canvas, a
	 *            scale factor of 0.5 means 1 pixel in the screen image is
	 *            displayed as 2 pixel on the canvas, etc.
	 * @see MultiResolutionRenderer
	 */
	public BdvOptions screenScales( final double[] s )
	{
		values.screenScales = s;
		return this;
	}

	/**
	 * Set target rendering time in nanoseconds.
	 *
	 * @param t
	 *            Target rendering time in nanoseconds. The rendering time for
	 *            the coarsest rendered scale should be below this threshold.
	 * @see MultiResolutionRenderer
	 */
	public BdvOptions targetRenderNanos( final long t )
	{
		values.targetRenderNanos = t;
		return this;
	}

	/**
	 * Set whether to used double buffered rendering.
	 *
	 * @param d
	 *            Whether to use double buffered rendering.
	 * @see MultiResolutionRenderer
	 */
	public BdvOptions doubleBuffered( final boolean d )
	{
		values.doubleBuffered = d;
		return this;
	}

	/**
	 * Set how many threads to use for rendering.
	 *
	 * @param n
	 *            How many threads to use for rendering.
	 * @see MultiResolutionRenderer
	 */
	public BdvOptions numRenderingThreads( final int n )
	{
		values.numRenderingThreads = n;
		return this;
	}

	public BdvOptions transformEventHandlerFactory( final TransformEventHandlerFactory< AffineTransform3D > f )
	{
		values.transformEventHandlerFactory = f;
		return this;
	}

	/**
	 * Set the factory for creating {@link AccumulateProjector}. This can be
	 * used to customize how sources are combined.
	 *
	 * @param f
	 *            factory for creating {@link AccumulateProjector}.
	 * @see MultiResolutionRenderer
	 */
	public BdvOptions accumulateProjectorFactory( final AccumulateProjectorFactory< ARGBType > f )
	{
		values.accumulateProjectorFactory = f;
		return this;
	}

	/**
	 * Set the {@link InputTriggerConfig} from which keyboard and mouse action mapping is loaded.
	 *
	 * @param c the {@link InputTriggerConfig} from which keyboard and mouse action mapping is loaded
	 */
	public BdvOptions inputTriggerConfig( final InputTriggerConfig c )
	{
		values.inputTriggerConfig = c;
		return this;
	}

	/**
	 * Set the transform of the {@link BdvSource} to be created.
	 *
	 * @param t
	 *            the source transform.
	 */
	public BdvOptions sourceTransform( final AffineTransform3D t )
	{
		values.sourceTransform.set( t );
		return this;
	}

	public BdvOptions frameTitle( final String title )
	{
		values.frameTitle = title;
		return this;
	}

	/**
	 * Set the transform of the {@link BdvSource} to be created to account for
	 * the given calibration (scaling of the source axes).
	 *
	 * @param calibration
	 *            the source calibration (scaling of the source axes).
	 */
	public BdvOptions sourceTransform( final double[] calibration )
	{
		final double sx = calibration.length >= 1 ? calibration[ 0 ] : 1;
		final double sy = calibration.length >= 2 ? calibration[ 1 ] : 1;
		final double sz = calibration.length >= 3 ? calibration[ 2 ] : 1;
		values.sourceTransform.set(
				sx, 0, 0, 0,
				0, sy, 0, 0,
				0, 0, sz, 0 );
		return this;
	}

	/**
	 * Read-only {@link BdvOptions} values.
	 */
	public static class Values
	{
		private int width = 800;

		private int height = 600;

		private double[] screenScales = new double[] { 1, 0.75, 0.5, 0.25, 0.125 };

		private long targetRenderNanos = 30 * 1000000l;

		private boolean doubleBuffered = true;

		private int numRenderingThreads = 3;

		private TransformEventHandlerFactory< AffineTransform3D > transformEventHandlerFactory = BehaviourTransformEventHandler3D.factory();

		private AccumulateProjectorFactory< ARGBType > accumulateProjectorFactory = AccumulateProjectorARGB.factory;

		private InputTriggerConfig inputTriggerConfig = null;

		private final AffineTransform3D sourceTransform = new AffineTransform3D();

		private String frameTitle = "BigDataViewer";

		Values()
		{
			sourceTransform.identity();
		}

		public BdvOptions optionsFromValues()
		{
			return new BdvOptions().
				width( width ).
				height( height ).
				screenScales( screenScales ).
				targetRenderNanos( targetRenderNanos ).
				doubleBuffered( doubleBuffered ).
				numRenderingThreads( numRenderingThreads ).
				transformEventHandlerFactory( transformEventHandlerFactory ).
				accumulateProjectorFactory( accumulateProjectorFactory ).
				inputTriggerConfig( inputTriggerConfig ).
				sourceTransform( sourceTransform );
		}

		public ViewerOptions getViewerOptions()
		{
			return ViewerOptions.options().
				width( width ).
				height( height ).
				screenScales( screenScales ).
				targetRenderNanos( targetRenderNanos ).
				doubleBuffered( doubleBuffered ).
				numRenderingThreads( numRenderingThreads ).
				transformEventHandlerFactory( transformEventHandlerFactory ).
				accumulateProjectorFactory( accumulateProjectorFactory ).
				inputTriggerConfig( inputTriggerConfig );
		}

		public AffineTransform3D getSourceTransform()
		{
			return sourceTransform;
		}

		public String getFrameTitle()
		{
			return frameTitle;
		}
	}
}