package bbr.b2b.portal.classes.utils.exagon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entity")
public class Node {

	@XmlElement(name = "name")
	private String Name;

	@XmlElement(name = "type")
	private String Type;

	@XmlElement(name = "readonly_flag")
	private Boolean ReadonlyFlag;

	@XmlElement(name = "leaf_flag")
	private Boolean LeafFlag;

	@XmlElement(name = "entity")
	private Node[] Nodes;

	public Boolean getLeafFlag() {
		return LeafFlag;
	}

	public String getName() {
		return Name;
	}

	public Node[] getNodes() {
		return Nodes;
	}

	public Boolean getReadonlyFlag() {
		return ReadonlyFlag;
	}

	public String getType() {
		return Type;
	}

	public void setLeafFlag(Boolean leafFlag) {
		LeafFlag = leafFlag;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setNodes(Node[] nodes) {
		Nodes = nodes;
	}

	public void setReadonlyFlag(Boolean readonlyFlag) {
		ReadonlyFlag = readonlyFlag;
	}

	public void setType(String type) {
		Type = type;
	}

}
