package repository.xmlRepository;


import domain.Grades;
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

public class GradeXmlRepository extends XmlRepository<Grades> {

	public GradeXmlRepository(Validator<Grades> validator, String xmlFilePath) {
		super(validator, xmlFilePath);
		try {
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Optional<Grades> save(Grades entity) throws ValidatorException{
		Optional<Grades> optionalGrades = super.save(entity);
		
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse("data/grades.xml");
			
			Element root = doc.getDocumentElement();
			Element gradeElement = doc.createElement("grades");
			root.appendChild(gradeElement);
			appendChildWithText(doc, gradeElement, "id", entity.getId().toString());
			appendChildWithText(doc, gradeElement, "problemID", String.valueOf(entity.GetProblemId()));
			appendChildWithText(doc, gradeElement, "studentID", String.valueOf(entity.GetStudentId()));
			appendChildWithText(doc, gradeElement, "grade", String.valueOf(entity.GetGrade()));
			
			Transformer transf = TransformerFactory.newInstance().newTransformer();
			transf.transform(new DOMSource(root), new StreamResult(new FileOutputStream("./data/grades.xml")));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return optionalGrades;
	}

	@Override
	public void loadData() throws Exception {
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		
		Document doc = docBuild.parse("data/grades.xml");
		Element root = doc.getDocumentElement();
		
		NodeList nodes = root.getChildNodes();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node gradeNode = nodes.item(i);
			if (gradeNode instanceof Element) {
				Grades grade = createGrade((Element) gradeNode);
				super.save(grade);
			}
		}
		
	}

	private Grades createGrade(Element gradeNode) {
		Grades grade = new Grades();

		grade.setId(Long.valueOf(getTextFromTagName(gradeNode, "id")));
		grade.SetProblemId(Integer.valueOf(getTextFromTagName(gradeNode, "problemID")));
		grade.SetStudentId(Integer.valueOf(getTextFromTagName(gradeNode, "studentID")));
		grade.SetGrade(Integer.valueOf(getTextFromTagName(gradeNode, "grade")));
		
		return grade;
	}

}

