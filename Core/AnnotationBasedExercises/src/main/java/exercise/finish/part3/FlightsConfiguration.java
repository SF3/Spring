package exercise.finish.part3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "exercise.finish.part3")
public class FlightsConfiguration {
    @Bean(name = "bookingService")
    public FlightBookingService foo() {
        return new FlightBookingServiceMock();
    }

    @Bean(name = "searchEngine")
    public FlightSearchEngine bar() {
        return new FlightSearchEngineMock();
    }

    @Bean(name = "bookingController")
    public BookingController zed() {
        return new BookingControllerMock();
    }
}
