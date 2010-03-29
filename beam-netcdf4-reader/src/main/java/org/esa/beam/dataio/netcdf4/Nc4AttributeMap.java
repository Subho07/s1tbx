package org.esa.beam.dataio.netcdf4;

import ucar.nc2.Attribute;
import ucar.nc2.Group;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class which provides a fast, hash map based access to NetCDF attribute lists.
 *
 * @author Norman Fomferra
 */
public class Nc4AttributeMap {

    private Map<String, Attribute> _map;

    public Nc4AttributeMap(int initialCapacity) {
        _map = new HashMap<String, Attribute>(initialCapacity);
    }

    public Nc4AttributeMap(Attribute[] attributes) {
        this((3 * attributes.length) / 2 + 1);
        for (Attribute attribute : attributes) {
            put(attribute);
        }
    }

    private Nc4AttributeMap(List<Attribute> attributes) {
        this(attributes.toArray(new Attribute[attributes.size()]));
    }

    public static Nc4AttributeMap create(NetcdfFile file) {
        return new Nc4AttributeMap(file.getGlobalAttributes());
    }

    public static Nc4AttributeMap create(Group group) {
        return new Nc4AttributeMap(group.getAttributes());
    }

    public static Nc4AttributeMap create(Variable variable) {
        return new Nc4AttributeMap(variable.getAttributes());
    }

    public Attribute get(String name) {
        return _map.get(name);
    }

    public void put(Attribute attribute) {
        _map.put(attribute.getName(), attribute);
    }

    /**
     * Removes all attributes from this map.
     */
    public void clear() {
        _map.clear();
    }

    public Number getNumericValue(String name) {
        final Attribute attribute = get(name);
        return attribute != null ? attribute.getNumericValue() : null;
    }

    public String getStringValue(String name) {
        final Attribute attribute = get(name);
        return attribute != null ? attribute.getStringValue() : null;
    }

    public int getValue(String name, int defaultValue) {
        final Number numericValue = getNumericValue(name);
        return numericValue != null ? numericValue.intValue() : defaultValue;
    }

    public double getValue(String name, double defaultValue) {
        final Number numericValue = getNumericValue(name);
        return numericValue != null ? numericValue.doubleValue() : defaultValue;
    }

    public String getValue(String name, String defaultValue) {
        final String stringValue = getStringValue(name);
        return stringValue != null ? stringValue : defaultValue;
    }
}
