package repository.xmlRepository;


import domain.AssignProblem;
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

public class AssignProblemXmlRepository extends XmlRepository<AssignProblem> {

	public AssignProblemXmlRepository(Validator<AssignProblem> validator, String xmlFilePath) {
		super(validator, xmlFilePath);
		try {
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Optional<AssignProblem> save(AssignProblem entity) throws ValidatorException{
		Optional<AssignProblem> optionalDiscipline = super.save(entity);
		
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse("data/assignProblem.xml");
			
			Element root = doc.getDocumentElement();
			Element assignElem = doc.createElement("assignProblem");
			root.appendChild(assignElem);
			appendChildWithText(doc, assignElem, "id", entity.getId().toString());
			appendChildWithText(doc, assignElem, "problemId", String.valueOf(entity.getProblemId()));
			appendChildWithText(doc, assignElem, "studentId", String.valueOf(entity.getStudentId()));
			appendChildWithText(doc, assignElem, "frequncy", String.valueOf(entity.getFrequency()));
			
			Transformer transf = TransformerFactory.newInstance().newTransformer();
			transf.transform(new DOMSource(root), new StreamResult(new FileOutputStream("./data/assignProblem.xml")));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return optionalDiscipline;
	}

	@Override
	public void loadData() throws Exception {
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		
		Document doc = docBuild.parse("data/assignProblem.xml");
		Element root = doc.getDocumentElement();
		
		NodeList nodes = root.getChildNodes();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node assignProblemNode = nodes.item(i);
			if (assignProblemNode instanceof Element) {
				AssignProblem assignProblem = createAssign((Element) assignProblemNode);
				super.save(assignProblem);
			}
		}
		
	}

	private AssignProblem createAssign(Element assignProblemNode) {
		AssignProblem assign = new AssignProblem();

		assign.setId(Long.valueOf(getTextFromTagName(assignProblemNode, "id")));
		assign.setProblemId(Integer.valueOf(getTextFromTagName(assignProblemNode, "problemId")));
		assign.setStudentId(Integer.valueOf(getTextFromTagName(assignProblemNode, "studentId")));
		assign.setFrequency(Integer.valueOf(getTextFromTagName(assignProblemNode, "frequency")));
		
		return assign;
	}

}
