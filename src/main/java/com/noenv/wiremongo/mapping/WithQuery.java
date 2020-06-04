package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithQuery<T, C extends WithQuery<T, C>> extends WithCollection<T, C> {

  public abstract static class WithQueryCommand extends WithCollectionCommand {

    private final JsonObject query;

    public WithQueryCommand(String method, String collection, JsonObject query) {
      super(method, collection);
      this.query = query;
    }

    @Override
    public String toString() {
      return super.toString() + ", query: " + query;
    }
  }

  private Matcher<JsonObject> query;

  public WithQuery(String method) {
    super(method);
  }

  public WithQuery(JsonObject json) {
    super(json);
    query = Matcher.create(json.getJsonObject("query"));
  }

  public C withQuery(JsonObject query) {
    return withQuery(equalTo(query));
  }

  public C withQuery(Matcher<JsonObject> query) {
    this.query = query;
    return self();
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithQueryCommand)) {
      return false;
    }
    WithQueryCommand c = (WithQueryCommand) cmd;
    return query == null || query.matches(c.query);
  }
}
