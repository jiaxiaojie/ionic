/**
 * 
 */
package test;
/**
 * @author yangtao
 *
 */
public class XmlToObjectTest {
	/*public static void main(String[] args) {
		try {
			String test = "<response platformNo=\"10012467598\"><code>1</code><code1>1</code1><description>操作成功</description><records><record><requestNo>08a584ed5d41408ea42bdae9000beba63005A1c426ae5168a</requestNo><bizType>REPAYMENT</bizType><amount>3.50</amount><status>CONFIRM</status><subStatus>SUCCESS</subStatus><sourceUserType>MEMBER</sourceUserType><sourceUserNo>c426ae516801</sourceUserNo><createdTime>2015-08-15 14:12:19</createdTime><completedTime>2015-08-17 13:34:07</completedTime></record><record><requestNo>08a584ed5d41408ea42bdae9000beba63005A1c426ae5168a</requestNo><bizType>REPAYMENT</bizType><amount>13.50</amount><amount1>13.50</amount1><amount2>13.50</amount2><status>CONFIRM</status><subStatus>SUCCESS</subStatus><sourceUserType>MEMBER</sourceUserType><sourceUserNo>c426ae516801</sourceUserNo><createdTime>2015-08-15 14:12:19</createdTime><completedTime>2015-08-17 13:34:07</completedTime></record></records></response>";
			QueryCpTranscationRespA resp = JaxbMapper.fromXml(test,
					QueryCpTranscationRespA.class);
			System.out.println("1111======="
					+ resp.getRecords().get(0).getAmount());
			System.out.println("1111======="
					+ resp.getRecords().get(1).getAmount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> QueryCpTranscationRespA unmarshallList(
			Class<QueryCpTranscationRespA> clazz, String xmlPath, String xsdPath)
			throws Exception {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller ums = context.createUnmarshaller();
		if (xsdPath != null) {
			try {
				File xsdFile = new File(xsdPath);
				Schema schema = SchemaFactory.newInstance(
						XMLConstants.OUTPUT_XML_CHARACTER_ENCODING).newSchema(
						xsdFile);
				ums.setSchema(schema);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		QueryCpTranscationRespA jaxbList = (QueryCpTranscationRespA) ums
				.unmarshal(new File(xmlPath));
		return jaxbList;
	}*/
}
