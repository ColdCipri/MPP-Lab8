package repository.xmlRepository;

import domain.Discipline;
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

public class DisciplineXmlRepository extends XmlRepository<Discipline> {

	public DisciplineXmlRepository(Validator<Discipline> validator, String xmlFilePath) {
		super(validator, xmlFilePath);
		try {
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Optional<Discipline> save(Discipline entity) throws ValidatorException{
		Optional<Discipline> optionalDiscipline = super.save(entity);
		
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse("data/disciplines.xml");
			
			Element root = doc.getDocumentElement();
			Element discipElement = doc.createElement("student");
			root.appendChild(discipElement);
			appendChildWithText(doc, discipElement, "id", entity.getId().toString());
			appendChildWithText(doc, discipElement, "title", entity.getTitle());
			appendChildWithText(doc, discipElement, "credits", String.valueOf(entity.getCredits()));
			
			Transformer transf = TransformerFactory.newInstance().newTransformer();
			transf.transform(new DOMSource(root), new StreamResult(new FileOutputStream("./data/disciplines.xml")));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return optionalDiscipline;
	}

	@Override
	public void loadData() throws Exception {
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		
		Document doc = docBuild.parse("data/disciplines.xml");
		Element root = doc.getDocumentElement();
		
		NodeList nodes = root.getChildNodes();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node disciplineNode = nodes.item(i);
			if (disciplineNode instanceof Element) {
				Discipline discipline = createDiscipline((Element) disciplineNode);
				super.save(discipline);
			}
		}
		
	}

	private Discipline createDiscipline(Element disciplineNode) {
		Discipline discip = new Discipline();

		discip.setId(Long.valueOf(getTextFromTagName(disciplineNode, "id")));
		discip.setTitle(getTextFromTagName(disciplineNode, "title"));
		discip.setCredits(Integer.valueOf(getTextFromTagName(disciplineNode, "credits")));
		
		return discip;
	}

}
