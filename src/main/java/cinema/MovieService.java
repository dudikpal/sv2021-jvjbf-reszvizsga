package cinema;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class MovieService {

    private List<Movie> movies = new ArrayList<>();

    private AtomicLong idGenerator = new AtomicLong();

    private ModelMapper modelMapper;

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<MovieDTO> getMovies(Optional<String> title) {
        return movies.stream()
                .filter(movie -> title.isEmpty() || movie.getTitle().equalsIgnoreCase(title.get()))
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }


    public Movie findMovieById(long id) {
        return movies.stream()
                .filter(movie -> movie.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


    public void deleteAll() {
        idGenerator = new AtomicLong();
        movies.clear();
    }


    public MovieDTO getMovie(long id) {
        return modelMapper.map(findMovieById(id), MovieDTO.class);
    }


    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(),
                command.getTitle(),
                command.getDate(),
                command.getMaxSpaces());
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }


    public MovieDTO reserveSpaces(long id, CreateReservationCommand command) {
        Movie movie = findMovieById(id);
        movie.reserve(command.getSpaces());
        return modelMapper.map(movie, MovieDTO.class);
    }


    public MovieDTO updateMovieDate(long id, UpdateDateCommand command) {
        Movie movie = findMovieById(id);
        movie.setDate(command.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }
}
