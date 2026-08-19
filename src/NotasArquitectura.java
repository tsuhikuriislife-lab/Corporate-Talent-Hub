public class NotasArquitectura {

    /*
     * Java 7 vs Java 17/21 Architectual Differences:
     * 
     * 1. Release and Support Cadence:
     * - Java 7: Worked with monolithic releases that had years between each of
     * them.
     * - Java 17/21: Have a 6-month predictable feature release cycle with 2-year
     * LTS cycles.
     * 
     * 2. Developer Ergonomics & Boilerplate:
     * - Java 7: Had a heavy reliance on traditional OS-mapped threads, making
     * high-concurrency expensive.
     * - Java 17/21: Have Virtual Threads, allowing millions of lightweight threads
     * and fundamentally changing high-throughput microservices.
     * 
     * 3. Data Modelling and Domain Logic:
     * - Java 7: Rigid object-oriented modeling with verbose getters and setters.
     * - Java 17/21: Modern Java provides domain-driven design clarity with Sealed
     * Classes and immutable data carriers.
     * 
     * 4. Performance and Memory:
     * - Java 7: Standard garbage collector models, suited for single-server
     * enterprise apps with predictable heaps.
     * - Java 17/21: Modern Java features advanced low-pause garbage collectors,
     * significantly faster startup times, and lower cloud memory footprints.
     */
}
