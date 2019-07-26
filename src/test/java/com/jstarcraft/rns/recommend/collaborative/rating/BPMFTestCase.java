package com.jstarcraft.rns.recommend.collaborative.rating;

import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.ai.evaluate.rating.MAEEvaluator;
import com.jstarcraft.ai.evaluate.rating.MPEEvaluator;
import com.jstarcraft.ai.evaluate.rating.MSEEvaluator;
import com.jstarcraft.rns.configure.Configurator;
import com.jstarcraft.rns.recommend.collaborative.rating.BPMFRecommender;
import com.jstarcraft.rns.task.RatingTask;

public class BPMFTestCase {

	@Test
	public void testRecommender() throws Exception {
		Configurator configuration = Configurator.valueOf("recommend/collaborative/rating/bpmf-test.properties");
		RatingTask job = new RatingTask(BPMFRecommender.class, configuration);
		Map<String, Float> measures = job.execute();
		Assert.assertThat(measures.get(MAEEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.64812225F));
		Assert.assertThat(measures.get(MPEEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.9840845F));
		Assert.assertThat(measures.get(MSEEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.67839986F));
	}

}
