package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof Transition) {
				if(!((Transition) content).getSource().getName().isEmpty()) {
					System.out.println(((Transition) content).getSource().getName()+ " -> " +(((Transition) content).getTarget().getName()));
				}
			}
			if(content instanceof State) {
				State state = (State) content;
				if(state.getOutgoingTransitions().size()>0) {
					System.out.println(state.getName());
				}
				else if(state.getOutgoingTransitions().size()==0 && !state.getName().isEmpty()){
					System.out.println(state.getName()+ " (csapda állapot)");
				}
				else if(state.getName().isEmpty()) {
					System.out.println("Ennek az állapotnak nincs neve, javasolt nevek: Stop, End, StopState");
				}
			}
			
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}