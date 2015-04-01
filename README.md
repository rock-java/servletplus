# servletplus
Enhance servlet 3+.


#comming soon!

## Examples
```java
@WebServlet("/user/*")
public class UserServlet extends Servlet {
	public UserServlet() {
		super();
		subRouter = new BasicSubRouter(this);
		subRouter.get("/{id}", (Request req, Response res)->{res.json(user);});
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