package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Embeddable
@Accessors(chain = true)
public class Rating {
    private float rate;
    private float scale;
}
