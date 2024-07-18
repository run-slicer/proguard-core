package proguard.util;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * A basic logger wrapper.
 *
 * @author Matouš Kučera
 */
public abstract class Logger {
  private static Factory FACTORY =
      ignored ->
          new Logger() {
            @Override
            public void log(@NotNull Level level, Object msg, Object... args) {
              // no-op
            }
          };

  /**
   * Logs a message with the {@link Level#TRACE} level.
   *
   * @param msg the message
   * @param args the formatting arguments
   */
  public void trace(Object msg, Object... args) {
    this.log(Level.TRACE, msg, args);
  }

  /**
   * Logs a message with the {@link Level#DEBUG} level.
   *
   * @param msg the message
   * @param args the formatting arguments
   */
  public void debug(Object msg, Object... args) {
    this.log(Level.DEBUG, msg, args);
  }

  /**
   * Logs a message with the {@link Level#INFO} level.
   *
   * @param msg the message
   * @param args the formatting arguments
   */
  public void info(Object msg, Object... args) {
    this.log(Level.INFO, msg, args);
  }

  /**
   * Logs a message with the {@link Level#WARN} level.
   *
   * @param msg the message
   * @param args the formatting arguments
   */
  public void warn(Object msg, Object... args) {
    this.log(Level.WARN, msg, args);
  }

  /**
   * Logs a message with the {@link Level#ERROR} level.
   *
   * @param msg the message
   * @param args the formatting arguments
   */
  public void error(Object msg, Object... args) {
    this.log(Level.ERROR, msg, args);
  }

  /**
   * Logs a message with an arbitrary level.
   *
   * @param level the log level
   * @param msg the message
   * @param args the formatting arguments
   */
  public abstract void log(@NotNull Level level, Object msg, Object... args);

  // static factories

  /**
   * Creates a new named logger.
   *
   * @param name the logger name
   * @return the logger
   */
  public static @NotNull Logger getLogger(@NotNull String name) {
    return FACTORY.create(Objects.requireNonNull(name, "name"));
  }

  /**
   * Creates a new named logger for a specific class.
   *
   * @param clazz the class
   * @return the logger
   */
  public static @NotNull Logger getLogger(@NotNull Class<?> clazz) {
    return FACTORY.create(Objects.requireNonNull(clazz, "clazz"));
  }

  /**
   * Sets a new {@link Factory}.
   *
   * @param factory the factory
   */
  public static void setFactory(@NotNull Factory factory) {
    FACTORY = Objects.requireNonNull(factory, "factory");
  }

  /** Log levels. */
  public enum Level {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR
  }

  /** A logger factory. */
  @FunctionalInterface
  public interface Factory {
    /**
     * Creates a new named logger.
     *
     * @param name the logger name
     * @return the logger
     */
    @NotNull
    Logger create(@NotNull String name);

    /**
     * Creates a new named logger for a specific class.
     *
     * @param clazz the class
     * @return the logger
     */
    default @NotNull Logger create(@NotNull Class<?> clazz) {
      return this.create(clazz.getName());
    }
  }
}
