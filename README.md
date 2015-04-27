# servletplus
Enhance servlet 3+.


#Installation



## Require:
**Java 8+,Servlet 3.0+**

## Examples:
```java
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.rockson.servletplus.BasicSubRouter;
import com.rockson.servletplus.Next;
import com.rockson.servletplus.Servlet;
import com.rockson.servletplus.Request;
import com.rockson.servletplus.Response;

@WebServlet("/user/*")
public class UserServlet extends Servlet {
	public UserServlet() {
		subRouter = new BasicSubRouter(this);
		// regular expression path, path should start with ^
		subRouter.use("^/candy.*$", (Request req, Response res, Next next) -> {
			System.out.println("use candy - "+req.getRequestURI());
			next.apply();
		});
		subRouter.get("/{id}", (Request req, Response res, Next next) -> {
			System.out.println("get {id} - "+req.getRequestURI());
			next.apply();
		});
		subRouter.get("/{id}", (Request req, Response res) -> {
			res.json(user);
		});
		//anonymous group and named group
		subRouter.get("^/candy/(\\d+)/(?<g1>\\w+)$", (Request req, Response res) -> {
			res.json(req.getPathParams());
		});
		subRouter.get("/{id}/{property}", this::userProperty);
	}

	protected void userProperty(Request req, Response res) throws ServletException, IOException {
		res.send(user.get(req.getPathParam("property")));
	}

	@Override
	protected void get(Request req, Response res) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/signin.jsp").forward(req, res);
	}

	Map<String, String> user = new HashMap<String, String>();
	{
		user.put("id", "123");
		user.put("name", "jim");
		user.put("addr", "moon");
	}

}
```

## Features:
* Easy to use.
* Powerful.
* Flexible.

## License
[MIT](https://github.com/rock-java/servletplus/blob/master/LICENSE)
