package demos.spring.boot.flights.validators;

import demos.spring.boot.flights.beans.SearchBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component("flightSearchValidator")
public class FlightSearchValidator implements Validator {

    public boolean supports(Class klass) {
        return SearchBean.class.isAssignableFrom(klass);
    }

    public void validate(Object target, Errors errors) {
        SearchBean bean = (SearchBean) target;
        Date departing = null;
        Date returning = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            departing = df.parse(bean.getDateOfDeparture());
            bean.setDepartDate(departing);
        } catch (ParseException pe) {
            errors.reject("badDepartureDate", "Your date of departure cannot be understood");
        }
        try {
            returning = df.parse(bean.getDateOfReturn());
            bean.setReturnDate(returning);
        } catch (ParseException pe) {
            errors.reject("badDepartureDate", "Your date of return cannot be understood");
        }
        if (bean.getOrigin().equals(bean.getDestination())) {
            errors.reject("sameAirports", "Source and Destination Airports Must Be Different!");
        }
        if (departing != null && returning != null) {
            if (departing.after(returning)) {
                errors.reject("datesInvalid", "You Have to Leave Before You Arrive!");
            }
        }
    }

}
