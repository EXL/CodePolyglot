package ru.exlmoto.code.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.exlmoto.code.controller.helper.ControllerTestHelper;
import ru.exlmoto.code.helper.ResourceHelper;
import ru.exlmoto.code.service.DatabaseService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ResourceHelper resource;

	@MockBean
	private DatabaseService database;

	private final ControllerTestHelper helper = new ControllerTestHelper();

	@Test
	public void testRaw() throws Exception {
		doReturn(Optional.empty()).when(database).getCodeSnippet(anyLong());

		helper.validatePlainTextUtf8(mvc, "/api/raw/100", "Error");

		helper.checkError4xx(mvc, "/api/raw");
		helper.checkError4xx(mvc, "/api/raw/100/unknown");
	}

	@Test
	public void testHighlight() throws Exception {
		helper.checkUnauthorizedPlainTextWithoutCsrf(mvc, "/api", "Error");

		String in = resource.readFileToString("classpath:/snippets/Java.snippet.txt");
		String out = resource.readFileToString("classpath:/expected/HighlightJs/Java.html.txt");
		helper.checkUnauthorizedPlainTextWithoutCsrfWithData(mvc, "/api?o=java", in, out);

		helper.checkError4xx(mvc, "/api/unknown");
	}

	@Test
	public void testCss() throws Exception {
		helper.validateCssUtf8(mvc, "/api/css", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno&mode=HighlightRouge", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno&mode=HighlightPygments", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=unknown&mode=HighlightPygments", "Error");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno&mode=unknown", "Error");

		helper.checkError4xx(mvc, "/api/css/unknown");
	}

	@Test
	public void testVersions() throws Exception {
		helper.validateJsonUtf8(mvc, "/api/versions");

		helper.checkError4xx(mvc, "/api/versions/unknown");
	}
}
