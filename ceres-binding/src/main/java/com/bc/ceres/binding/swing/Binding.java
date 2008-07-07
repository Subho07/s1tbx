package com.bc.ceres.binding.swing;

import javax.swing.JComponent;

/**
 * A bi-directional binding between one or more Swing components and a property in a value container.
 * <p/>
 * Classes derived from {@code Binding} are asked to add an appropriate change listener to their Swing component
 * which will transfer the value from the component into the associated {@link com.bc.ceres.binding.ValueContainer}
 * using the {@link #setPropertyValue(Object)} method.
 * <p/>
 * Whenever the value of the named property changes in the {@link com.bc.ceres.binding.ValueContainer},
 * the template method {@link #adjustComponents()} will be called.
 * Clients implement the {@link ComponentAdapter#adjustComponents()} method
 * in order to adjust their Swing component according the new property value.
 * Note that {@code adjustComponents()} will <i>not</i> be recursively called again, if the
 * the property value changes while {@code adjustComponents()} is executed. In this case,
 * the value of the {@link #isAdjustingComponents() adjustingComponent} property will be {@code true}.
 * <p/>
 *
 * @author Norman Fomferra
 * @version $Revision$ $Date$
 * @since BEAM 4.2
 */
public interface Binding {
    /**
     * @return The binding context.
     */
    BindingContext getContext();

    /**
     * @return The component adapter.
     */
    ComponentAdapter getComponentAdapter();

    /**
     * @return The name of the bound property.
     */
    String getPropertyName();

    /**
     * @return The value of the bound property.
     */
    Object getPropertyValue();

    /**
     * Sets the value of the bound property.
     * This may trigger a property change event in the associated {@code ValueContainer}.
     * Any exception thrown during this operation will be delegated to {@link ComponentAdapter#handleError(Exception)}.
     *
     * @param value The new value of the bound property.
     */
    void setPropertyValue(Object value);

    /**
     * Adjusts the bound Swing components in reaction to a property change event in the
     * associated {@code ValueContainer}. Calls {@link ComponentAdapter#adjustComponents()}
     * only if this binding is not already adjusting the bound Swing components.
     * Any kind of exceptions thrown during execution of the method
     * will be handled by delegating the exception to
     * the {@link ComponentAdapter#handleError(Exception)} method.
     * @see #isAdjustingComponents()
     */
    void adjustComponents();

    /**
     * Tests if this binding is currently adjusting the bound Swing components.
     *
     * @return {@code true} if so.
     * @see #adjustComponents()
     */
    boolean isAdjustingComponents();

    /**
     * Gets the primary Swing component used for the binding, e.g. a {@link javax.swing.JTextField}.
     *
     * @return the primary Swing component.
     * @see #getComponents()
     */
    JComponent getPrimaryComponent();

    /**
     * Gets the components of the associated {@link ComponentAdapter} plus the ones added to this
     * binding using the {@link #addComponent(javax.swing.JComponent)} method.
     *
     * @return The components.
     */
    JComponent[] getComponents();

    /**
     * Adds a secondary Swing component to this binding, e.g. a {@link javax.swing.JLabel} whose
     * {@code enabled} or {@code visible} states will also be set by a call to {@link  #setComponentsEnabledState(boolean)}
     * or {@link #setComponentsVisibleState(boolean)}.
     *
     * @param component The secondary component.
     * @see #removeComponent(javax.swing.JComponent)
     */
    void addComponent(JComponent component);

    /**
     * Removed a secondary Swing component from this binding.
     *
     * @param component The secondary component.
     * @see #addComponent(javax.swing.JComponent)
     */
    void removeComponent(JComponent component);

    /**
     * Sets the <i>enabled</i> state of all components associated with this binding.
     * @param state The state to be propagated.
     */
    void setComponentsEnabledState(boolean state);

    /**
     * Sets the <i>visible</i> state of all components associated with this binding.
     * @param state The state to be propagated.
     */
    void setComponentsVisibleState(boolean state);
}
