package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.Scope;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
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
		
		//2.Feladat
		/*while (iterator.hasNext()) {
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
			
		}*/
		
		// 4.Feladat 3.alfeladat
		
		/*for(Scope scope : s.getScopes()) {
			System.out.println("Belső változók: ");
			for(Property p : scope.getVariables()) {
				System.out.println(p.getName());
			}
			System.out.println("Eventek: ");
			for(Event e : scope.getEvents()) {
				System.out.println(e.getName());
			}
		}*/
		
		// 4.Feladat 4.alfeladat
		
		/*for(Scope scope : s.getScopes()) {
			System.out.println("public static void print(IExampleStatemachine s) {");
			for(Property p : scope.getVariables()) {
				System.out.println("System.out.println(\"" + p.getName()+ "= \" + s.getSCInterface().get"+ p.getName()+ "());");
			}
			for(Event e : scope.getEvents()) {
				System.out.println("System.out.println(\"" + e.getName()+ "= \" + s.getSCInterface().get"+ e.getName()+ "());");
			}
			System.out.println("}");
			
		} */
		
		// 4.Feladat 5.alfeladat
		
		System.out.println("public class RunStatechart {\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		s.init();\r\n" + 
				"		s.enter();\r\n" + 
				"		s.runCycle();\r\n" + 
				"		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));\r\n" + 
				"		while(true) {\r\n" + 
				"			String name = reader.readLine();");
		
		for(Scope scope : s.getScopes()) {
			for(Event p : scope.getEvents()) {
				System.out.println("if(name.equals(\""+ p.getName()+ "\")) {\r\n" + 
							"				s.raise"+ p.getName()+ "();\r\n" + 
									"				s.runCycle();\r\n" + 
									"			}");
			}
			System.out.println("else if(name.equals(\"exit\")) {\r\n" + 
					"				print(s);\r\n" + 
					"				System.exit(0);\r\n" + 
					"			}\r\n" + 
					"			print(s);\r\n" + 
					"		}\r\n" + 
					"		\r\n" + 
					"	}\r\n" + 
					"\r\n" + 
					"	public static void print(IExampleStatemachine s) {");
		}
		for(Scope scope : s.getScopes()) {
			for(Property p : scope.getVariables()) {
				System.out.println("System.out.println(\"" + p.getName()+ "= \" + s.getSCInterface().get"+ p.getName()+ "());");
			}
		}
		System.out.println("}\r\n" + 
				"}");
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
