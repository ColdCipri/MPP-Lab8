package repository.xmlRepository;


import domain.Student;
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

public class StudentXmlRepository extends XmlRepository<Student> {

	public StudentXmlRepository(Validator<Student> validator, String xmlFilePath) {
		super(validator, xmlFilePath);
		try {
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Optional<Student> save(Student entity) throws ValidatorException{
		Optional<Student> optionalStudent = super.save(entity);
		
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse("data/students.xml");
			
			Element root = doc.getDocumentElement();
			Element studElement = doc.createElement("student");
			root.appendChild(studElement);
			appendChildWithText(doc, studElement, "id", entity.getId().toString());
			appendChildWithText(doc, studElement, "serialNumber", entity.getSerialNumber());
			appendChildWithText(doc, studElement, "name", entity.getName());
			appendChildWithText(doc, studElement, "group", String.valueOf(entity.getGroup()));
			
			Transformer transf = TransformerFactory.newInstance().newTransformer();
			transf.transform(new DOMSource(root), new StreamResult(new FileOutputStream("./data/students.xml")));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return optionalStudent;
	}

	@Override
	public void loadData() throws Exception {
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		
		Document doc = docBuild.parse("data/students.xml");
		Element root = doc.getDocumentElement();
		
		NodeList nodes = root.getChildNodes();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node studNode = nodes.item(i);
			if (studNode instanceof Element) {
				Student student = createStudent((Element) studNode);
				super.save(student);
			}
		}
		
	}

	private Student createStudent(Element studNode) {
		Student student = new Student();
		student.setId(Long.valueOf(getTextFromTagName(studNode, "id")));
		student.setSerialNumber(getTextFromTagName(studNode, "serialNumber"));
		student.setName(getTextFromTagName(studNode, "name"));
		student.setGroup(Integer.valueOf(getTextFromTagName(studNode, "group")));
		
		return student;
	}

}
