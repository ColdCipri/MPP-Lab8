package repository.xmlRepository;


import domain.Problem;
import domain.validator.Validator;
import domain.validator.ValidatorException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.Optional;

public class ProblemXmlRepository extends XmlRepository<Problem> {

	public ProblemXmlRepository(Validator<Problem> validator, String xmlFilePath) {
		super(validator, xmlFilePath);
		try {
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Optional<Problem> save(Problem entity) throws ValidatorException{
		Optional<Problem> optionalProblem = super.save(entity);
		
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse("data/problems.xml");
			
			Element root = doc.getDocumentElement();
			Element studElement = doc.createElement("problem");
			root.appendChild(studElement);
			appendChildWithText(doc, studElement, "id", entity.getId().toString());
			appendChildWithText(doc, studElement, "problemDescription", entity.getProblemDescription());
			appendChildWithText(doc, studElement, "problemName", entity.getProblemName());
			appendChildWithText(doc, studElement, "problemDifficulty", String.valueOf(entity.getProblemDifficulty()));
			
			Transformer transf = TransformerFactory.newInstance().newTransformer();
			transf.transform(new DOMSource(root), new StreamResult(new FileOutputStream("./data/problems.xml")));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return optionalProblem;
	}

	@Override
	public void loadData() throws Exception {
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		
		Document doc = docBuild.parse("data/problems.xml");
		Element root = doc.getDocumentElement();
		
		NodeList nodes = root.getChildNodes();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node problemNode = nodes.item(i);
			if (problemNode instanceof Element) {
				Problem problem = createProblem((Element) problemNode);
				super.save(problem);
			}
		}
		
	}

	private Problem createProblem(Element problemNode) {
		Problem problem = new Problem();

		problem.setId(Long.valueOf(getTextFromTagName(problemNode, "id")));
		problem.setProblemDescription((getTextFromTagName(problemNode, "problemDescription")));
		problem.setProblemName((getTextFromTagName(problemNode, "problemName")));
		problem.setProblemDifficulty(Integer.valueOf((getTextFromTagName(problemNode, "problemDifficulty"))));
		
		
		return problem;
	}

}
