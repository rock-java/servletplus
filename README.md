# servletplus
Enhance servlet 3+.


#Installation
```xml
<dependencies>
	<dependency>
		<groupId>com.rockson</groupId>
		<artifactId>servletplus</artifactId>
		<version>0.0.4</version>
	</dependency>
</dependencies>

<repositories>
	<repository>
		<id>servletplus-mvn-repo</id>
		<url>https://raw.github.com/rock-java/mvn-repo/master</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>

```


## Require:
**Java 8+,Servlet 3.0+**

## Examples:
```java
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.rockson.servletplus.BasicRouter;
import com.rockson.servletplus.Next;
import com.rockson.servletplus.Servlet;
import com.rockson.servletplus.Request;
import com.rockson.servletplus.Response;

@WebServlet("/user/*")
public class UserServlet extends Servlet {
	public UserServlet() {
		// regular expression path, path should start with ^
		router.all("^/candy.*$", (req, res, next) -> {
			System.out.println("use candy - "+req.getRequestURI());
			next.apply();
		});
		router.get("/{id}", (req, res, next) -> {
			System.out.println("get {id} - "+req.getRequestURI());
			next.apply();
		});
		router.get("/{id}", (req, res) -> {
			res.json(user);
		});
		router.get("/{id}/info", (req, res) -> {
			res.sendFile("/info.html"); //info.html should in webapp dir.
		});
		//anonymous group and named group
		router.get("^/candy/(\\d+)/(?<g1>\\w+)$", (req, res) -> {
			res.json(req.getPathParams());
		});
		router.get("/{id}/{property}", this::userProperty);
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
