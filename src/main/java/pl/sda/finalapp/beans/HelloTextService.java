package pl.sda.finalapp.beans;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class HelloTextService implements TextProvider {
    @Override
    public String getText() {
        return "Hello";
    }
}
