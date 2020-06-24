package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.mapping.Command;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndReplaceWithOptions extends FindOneAndReplaceBase<FindOneAndReplaceWithOptions> {

  public static class FindOneAndReplaceWithOptionsCommand extends FindOneAndReplaceBaseCommand {

    private final FindOptions findOptions;
    private final UpdateOptions updateOptions;

    public FindOneAndReplaceWithOptionsCommand(String collection, JsonObject query, JsonObject replace,
                                               FindOptions findOptions, UpdateOptions updateOptions) {
      super("findOneAndReplaceWithOptions", collection, query, replace);
      this.findOptions = findOptions;
      this.updateOptions = updateOptions;
    }

    @Override
    public String toString() {
      return super.toString() + ", findOptions: " + (findOptions != null ? findOptions.toJson().encode() : "null")
        + ", updateOptions: " + (updateOptions != null ? updateOptions.toJson().encode() : "null");
    }
  }

  private Matcher<FindOptions> findOptions;
  private Matcher<UpdateOptions> updateOptions;

  public FindOneAndReplaceWithOptions() {
    super("findOneAndReplaceWithOptions");
  }

  public FindOneAndReplaceWithOptions(JsonObject json) {
    super(json);
    this.findOptions = Matcher.create(json.getJsonObject("findOptions"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
    this.updateOptions = Matcher.create(json.getJsonObject("updateOptions"), j -> new UpdateOptions((JsonObject) j), UpdateOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindOneAndReplaceWithOptionsCommand)) {
      return false;
    }
    FindOneAndReplaceWithOptionsCommand c = (FindOneAndReplaceWithOptionsCommand) cmd;
    return (findOptions == null || findOptions.matches(c.findOptions))
      && (updateOptions == null || updateOptions.matches(c.updateOptions));
  }

  public FindOneAndReplaceWithOptions withFindOptions(FindOptions findOptions) {
    return withFindOptions(equalTo(findOptions));
  }

  public FindOneAndReplaceWithOptions withFindOptions(Matcher<FindOptions> findOptions) {
    this.findOptions = findOptions;
    return self();
  }

  public FindOneAndReplaceWithOptions withUpdateOptions(UpdateOptions updateOptions) {
    return withUpdateOptions(equalTo(updateOptions));
  }

  public FindOneAndReplaceWithOptions withUpdateOptions(Matcher<UpdateOptions> updateOptions) {
    this.updateOptions = updateOptions;
    return self();
  }
}