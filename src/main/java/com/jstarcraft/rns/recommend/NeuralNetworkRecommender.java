package com.jstarcraft.rns.recommend;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import com.jstarcraft.ai.data.DataModule;
import com.jstarcraft.ai.data.DataSpace;
import com.jstarcraft.rns.configure.Configurator;

/**
 * 神经网络推荐器
 * 
 * @author Birdy
 *
 */
public abstract class NeuralNetworkRecommender extends ModelRecommender {

	/**
	 * the dimension of input units
	 */
	protected int inputDimension;

	/**
	 * the dimension of hidden units
	 */
	protected int hiddenDimension;

	/**
	 * the activation function of the hidden layer in the neural network
	 */
	protected String hiddenActivation;

	/**
	 * the activation function of the output layer in the neural network
	 */
	protected String outputActivation;

	/**
	 * the learning rate of the optimization algorithm
	 */
	protected float learnRate;

	/**
	 * the momentum of the optimization algorithm
	 */
	protected float momentum;

	/**
	 * the regularization coefficient of the weights in the neural network
	 */
	protected float weightRegularization;

	/**
	 * the data structure that stores the training data
	 */
	protected INDArray inputData;

	/**
	 * the data structure that stores the predicted data
	 */
	protected INDArray outputData;

	protected MultiLayerNetwork network;

	protected abstract int getInputDimension();

	protected abstract MultiLayerConfiguration getNetworkConfiguration();

	@Override
	public void prepare(Configurator configuration, DataModule model, DataSpace space) {
		super.prepare(configuration, model, space);
		inputDimension = getInputDimension();
		hiddenDimension = configuration.getInteger("recommender.hidden.dimension");
		hiddenActivation = configuration.getString("recommender.hidden.activation");
		outputActivation = configuration.getString("recommender.output.activation");
		learnRate = configuration.getFloat("recommender.iterator.learnrate");
		momentum = configuration.getFloat("recommender.iterator.momentum");
		weightRegularization = configuration.getFloat("recommender.weight.regularization");
	}

	@Override
	protected void doPractice() {
		MultiLayerConfiguration configuration = getNetworkConfiguration();
		network = new MultiLayerNetwork(configuration);
		network.init();
		for (int iterationStep = 1; iterationStep <= numberOfEpoches; iterationStep++) {
			totalLoss = 0F;
			network.fit(inputData, inputData);
			totalLoss = (float) network.score();
			if (isConverged(iterationStep) && isConverged) {
				break;
			}
			currentLoss = totalLoss;
		}

		outputData = network.output(inputData);
	}

}
