package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Favorite;
import models.Movie;
import models.User;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static play.libs.Json.toJson;

public class ApplicationRest extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public ApplicationRest(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result addMovie() {
        Movie movie = formFactory.form(Movie.class).bindFromRequest().get();
        Movie saved = jpaApi.em().merge(movie);
        return ok(toJson(saved));
    }

    @Transactional(readOnly = true)
    public Result getTop(int size) {
        List<Movie> top = jpaApi.em().createQuery("select m from Movie m order by m.rating").setMaxResults(size).getResultList();
        return ok(toJson(top));
    }

    @Transactional(readOnly = true)
    public Result getDetail(long movieId) {
        Movie movie = jpaApi.em().createQuery("select m from Movie m where m.id = ?", Movie.class)
                .setParameter(0, movieId).getSingleResult();
        return ok(toJson(movie));
    }

    @Transactional()
    @BodyParser.Of(BodyParser.Json.class)
    public Result addToFavoritePost() {
        JsonNode json = request().body().asJson();
        long movieId = json.findPath("movieId").asLong();
        long favoriteId = json.findPath("favoriteId").asLong();
        Long userId = Long.valueOf(session().get("user"));
        Favorite favorite = jpaApi.em().createQuery("select f from Favorite f, m_user u where f.id = ? and u.id = ?", Favorite.class)
                .setParameter(0, favoriteId)
                .setParameter(1, userId)
                .getSingleResult();

        Optional<Movie> favoriteMovie = favorite.getMovies().stream().filter(m -> m.getId() == movieId).findFirst();
        if (!favoriteMovie.isPresent()) {
            Movie movie = jpaApi.em().find(Movie.class, movieId);
            favorite.getMovies().add(movie);
        }
        return ok();
    }

    @Transactional(readOnly = true)
    public Result favorite() {
        Long userId = Long.valueOf(session().get("user"));
        User user = jpaApi.em().find(User.class, userId);
        return ok(toJson(user.getFavorites()));
    }

    @Transactional(readOnly = true)
    public Result favoriteList(long favoriteId) {
        Favorite favorite = jpaApi.em().find(Favorite.class, favoriteId);
        return ok(toJson(favorite));
    }

    @Transactional()
    @BodyParser.Of(BodyParser.Json.class)
    public Result removeFavoriteMoviePost() {
        JsonNode json = request().body().asJson();
        long movieId = json.findPath("movieId").asLong();
        long favoriteId = json.findPath("favoriteId").asLong();
        Long userId = Long.valueOf(session().get("user"));
        Favorite favorite = jpaApi.em().createQuery("select f from Favorite f, m_user u where f.id = ? and u.id = ? and f in elements(u.favorites)", Favorite.class)
                .setParameter(0, favoriteId)
                .setParameter(1, userId)
                .getSingleResult();
        if (favorite != null) {
            favorite.getMovies().removeIf(movie -> movie.getId() == movieId);
        }
        return ok();
    }

    @Transactional(readOnly = true)
    public Result showFavoriteList() {
        Long userId = Long.valueOf(session().get("user"));
        User user = jpaApi.em().find(User.class, userId);
        return ok(toJson(user.getFavorites()));
    }

    @Transactional
    public Result createFavorite(String name) {
        Long userId = Long.valueOf(session().get("user"));
        User user = jpaApi.em().find(User.class, userId);
        Favorite f = new Favorite();
        f.setName(name);
        Favorite newFavorite = jpaApi.em().merge(f);
        user.getFavorites().add(newFavorite);
        return ok(toJson(user.getFavorites()));
    }

}